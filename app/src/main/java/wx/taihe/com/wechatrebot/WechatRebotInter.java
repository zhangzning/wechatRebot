package wx.taihe.com.wechatrebot;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import wx.taihe.com.wechatrebot.impl.WechatImplements;
import wx.taihe.com.wechatrebot.inter.WechatInterface;

public abstract class WechatRebotInter implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.contains("com.tencent.mm")){
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Context context = (Context)param.args[0];
                    Log.i("Wechat Rebot Log","初始化完成"+param.thisObject);
                    initChat(new WechatImplements(context,lpparam.classLoader));
                }
            });
        }
    }

    abstract void initChat(WechatInterface wechatInterface);

}
