package wx.taihe.com.wechatrebot;

import wx.taihe.com.wechatrebot.inter.WechatInterface;

public class WechatRebot extends WechatRebotInter{
    @Override
    void initChat(WechatInterface wechatInterface) {
        wechatInterface.autoRepeat();
        wechatInterface.addFriends();
        wechatInterface.addFriendsToUI();
    }
}
