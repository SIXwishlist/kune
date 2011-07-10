package cc.kune.chat.client;

import com.calclab.emite.core.client.xmpp.session.XmppSession;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.presence.PresenceManager;
import com.calclab.emite.im.client.roster.XmppRoster;
import com.calclab.emite.reconnect.client.SessionReconnect;
import com.calclab.emite.xep.avatar.client.AvatarManager;
import com.calclab.emite.xep.muc.client.RoomManager;
import com.calclab.emite.xep.storage.client.PrivateStorageManager;
import com.calclab.suco.client.Suco;

public class ChatInstances {

  public AvatarManager avatarManager;
  public ChatManager chatManager;
  public PresenceManager presenceManager;
  public PrivateStorageManager privateStorageManager;
  public RoomManager roomManager;
  public XmppRoster roster;
  public SessionReconnect sessionReconnect;
  public XmppSession xmppSession;

  public ChatInstances() {
    this.xmppSession = Suco.get(XmppSession.class);
    this.roster = Suco.get(XmppRoster.class);
    this.chatManager = Suco.get(ChatManager.class);
    this.roomManager = Suco.get(RoomManager.class);
    this.sessionReconnect = Suco.get(SessionReconnect.class);
    this.avatarManager = Suco.get(AvatarManager.class);
    this.presenceManager = Suco.get(PresenceManager.class);
    this.privateStorageManager = Suco.get(PrivateStorageManager.class);
  }
}