package wx.taihe.com.wechatrebot.inter;

import android.content.Context;

public abstract class WechatInterface {
    protected Context context;
    protected ClassLoader classLoader;

    public WechatInterface(Context context,ClassLoader classLoader){
        this.context = context;
        this.classLoader = classLoader;
    }

    public abstract void autoRepeat();

}
