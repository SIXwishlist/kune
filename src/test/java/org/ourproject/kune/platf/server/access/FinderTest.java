package org.ourproject.kune.platf.server.access;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.ContentNotFoundException;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.content.ContainerManager;
import org.ourproject.kune.platf.server.content.ContentManager;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.manager.RateManager;

import com.google.gwt.user.client.rpc.SerializableException;

public class FinderTest {

    private GroupManager groupManager;
    private ContainerManager containerManager;
    private ContentManager contentManager;
    private RateManager rateManager;
    private FinderServiceDefault finder;

    @Before
    public void createSession() {
        this.groupManager = createStrictMock(GroupManager.class);
        this.containerManager = createStrictMock(ContainerManager.class);
        this.contentManager = createStrictMock(ContentManager.class);
        this.rateManager = createStrictMock(RateManager.class);
        this.finder = new FinderServiceDefault(groupManager, containerManager, contentManager, rateManager);
    }

    @Test
    public void testDefaultGroupContent() throws SerializableException {
        Group userGroup = new Group();
        Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
        userGroup.setDefaultContent(descriptor);

        Content content = finder.getContent(new StateToken(), userGroup);
        assertSame(descriptor, content);
    }

    @Test
    public void testDefaultGroupContentHasDefLicense() throws SerializableException {
        Group userGroup = new Group();
        Content descriptor = TestDomainHelper.createDescriptor(1l, "title", "content");
        userGroup.setDefaultContent(descriptor);

        Content content = finder.getContent(new StateToken(), userGroup);
        assertSame(userGroup.getDefaultLicense(), content.getLicense());
    }

    @Test
    public void testCompleteToken() throws SerializableException {
        Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(1, "groupShortName", "toolName");
        Content descriptor = new Content();
        descriptor.setId(1l);
        descriptor.setFolder(container);

        expect(contentManager.find(2l)).andReturn(descriptor);
        replay(contentManager);

        Content content = finder.getContent(new StateToken("groupShortName", "toolName", "1", "2"), null);
        assertSame(descriptor, content);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndFolderMatch() throws SerializableException {
        Content descriptor = new Content();
        Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName2");
        descriptor.setFolder(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndToolMatch() throws SerializableException {
        Content descriptor = new Content();
        Container container = TestDomainHelper.createFolderWithId(1);
        descriptor.setFolder(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void contentAndGrouplMatch() throws SerializableException {
        Content descriptor = new Content();
        Container container = TestDomainHelper.createFolderWithIdAndGroupAndTool(5, "groupOther", "toolName");
        descriptor.setFolder(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1"), null);
        verify(contentManager);
    }

    @Test(expected = ContentNotFoundException.class)
    public void voyAJoder() throws SerializableException {
        finder.getContent(new StateToken(null, "toolName", "1", "2"), null);
    }

    @Test
    public void testDocMissing() throws SerializableException {
        Container container = new Container();
        expect(containerManager.find(1l)).andReturn(container);

        replay(containerManager);
        Content content = finder.getContent(new StateToken("groupShortName", "toolName", "1", null), null);
        assertNotNull(content);
        assertSame(container, content.getFolder());
        verify(containerManager);
    }

    @Test
    public void testFolderMissing() throws SerializableException {
        Group group = new Group();
        ToolConfiguration config = group.setToolConfig("toolName", new ToolConfiguration());
        Container container = config.setRoot(new Container());
        expect(groupManager.findByShortName("groupShortName")).andReturn(group);
        replay(groupManager);

        StateToken token = new StateToken("groupShortName", "toolName", null, null);
        Content content = finder.getContent(token, null);
        assertSame(container, content.getFolder());
        verify(groupManager);
    }

    @Test
    public void getGroupDefaultContent() throws SerializableException {
        Group group = new Group();
        Content descriptor = new Content();
        group.setDefaultContent(descriptor);
        expect(groupManager.findByShortName("groupShortName")).andReturn(group);
        replay(groupManager);

        Content content = finder.getContent(new StateToken("groupShortName", null, null, null), null);
        assertSame(descriptor, content);
        verify(groupManager);
    }

    @Test
    public void testDefaultUserContent() throws SerializableException {
        Content content = new Content();
        Group group = new Group();
        group.setDefaultContent(content);
        Content response = finder.getContent(new StateToken(), group);
        assertSame(content, response);
    }

    @Test(expected = ContentNotFoundException.class)
    public void testIds() throws SerializableException {
        Content descriptor = new Content();
        Container container = TestDomainHelper.createFolderWithIdAndToolName(5, "toolName");
        descriptor.setFolder(container);
        expect(contentManager.find(1l)).andReturn(descriptor);
        replay(contentManager);

        finder.getContent(new StateToken("groupShortName", "toolName", "5", "1a"), null);
        verify(contentManager);
    }

}
