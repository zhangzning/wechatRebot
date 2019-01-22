package wx.taihe.com.wechatrebot.impl;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import wx.taihe.com.wechatrebot.LogUtil;
import wx.taihe.com.wechatrebot.inter.WechatInterface;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;

public class WechatImplements extends WechatInterface{

    public WechatImplements(Context context, ClassLoader classLoader) {
        super(context, classLoader);
        Log.i("Wechat Rebot Log","初始化微信聊天机器人-聊天模块加载完成");
    }

    @Override
    public void autoRepeat() {
        //接收微信消息的类
        Class<?> receiveClass = XposedHelpers.findClass("com.tencent.mm.booter.notification.b",classLoader);
        XposedHelpers.findAndHookMethod(receiveClass, "b", String.class, String.class, int.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                final String userAccount = param.args[0].toString();
                final String userMessage = param.args[1].toString();
                Log.i("Wechat Rebot Log","收到微信消息!发件人:"+userAccount+",消息:"+userMessage);

                if(userMessage.contains("@帅逼")) {

                    //开始Hook发微信的内容
                    Class<?> kernelGClass = XposedHelpers.findClass("com.tencent.mm.kernel.g", classLoader);
                    //au.DK() 方法中得g.DQ()方法，返回static g类
                    Object g_b = XposedHelpers.callStaticMethod(kernelGClass, "DQ");
                    //
                    Object dJT = XposedHelpers.getObjectField(g_b, "dKW");


                    Class<?> p_Class = XposedHelpers.findClass("com.tencent.mm.ah.p", classLoader);
                    //获取用于发送微信消息的类h的父类m
                    Class<?> m_Class = XposedHelpers.findClass("com.tencent.mm.ah.m", classLoader);
                    //Hook p类的a(m,int)方法
                    Method a_Method = XposedHelpers.findMethodExact(p_Class, "a", m_Class, int.class);

                    Object a_Method_v = XposedHelpers.callStaticMethod(p_Class, "a", dJT);
                    Log.i("Dump Stack: ", "---------------start----------------");
                    Throwable ex = new Throwable();
                    StackTraceElement[] stackElements = ex.getStackTrace();
                    if (stackElements != null) {
                        for (int i = 0; i < stackElements.length; i++) {
                            Log.i("Dump Stack"+i+": ", stackElements[i].getClassName()+"----"+stackElements[i].getFileName()+"----" + stackElements[i].getLineNumber()+"----" +stackElements[i].getMethodName());
                        }
                    }
                    Log.i("Dump Stack: ", "---------------over----------------");

                    //调用h类,准备给要发送的消息赋值
                    Class<?> h_Class_sendMessageValue = XposedHelpers.findClass("com.tencent.mm.modelmulti.h", classLoader);
                    //赋值
                    Object h = XposedHelpers.newInstance(h_Class_sendMessageValue, new Class[]{String.class, String.class, int.class, int.class, Object.class}, userAccount, "接收到:'" + userAccount + "',发送的消息:" + userMessage, 1, 1, new HashMap<String, String>() {{
                        put(userAccount, userAccount);
                    }});
                    //做a(m,int)的参数
                    Object[] aValue = new Object[]{h, 0};
                    //测试调用
                    XposedBridge.invokeOriginalMethod(a_Method, a_Method_v, aValue);
                    LogUtil.i(kernelGClass, g_b, a_Method_v);


/*
                    Log.i("Dump Stack: ", "---------------start----------------");
                    Throwable ex = new Throwable();
                    StackTraceElement[] stackElements = ex.getStackTrace();
                    if (stackElements != null) {
                        for (int i = 0; i < stackElements.length; i++) {
                            Log.i("Dump Stack"+i+": ", stackElements[i].getClassName()+"----"+stackElements[i].getFileName()+"----" + stackElements[i].getLineNumber()+"----" +stackElements[i].getMethodName());
                        }
                    }
                    Log.i("Dump Stack: ", "---------------over----------------");*/
                }

            }
        });
        Class<?> ajClass = XposedHelpers.findClass("com.tencent.mm.sdk.platformtools.aj",classLoader);
        Class<?> hClass = XposedHelpers.findClass("android.os.Message",classLoader);
        XposedHelpers.findAndHookConstructor(ajClass,hClass,new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.i("Dump Stack: ", "---------------start----------------");
                Throwable ex = new Throwable();
                StackTraceElement[] stackElements = ex.getStackTrace();
                if (stackElements != null) {
                    for (int i = 0; i < stackElements.length; i++) {
                        Log.i("Dump Stack"+i+": ", stackElements[i].getClassName()+"----"+stackElements[i].getFileName()+"----" + stackElements[i].getLineNumber()+"----" +stackElements[i].getMethodName());
                    }
                }
                Log.i("Dump Stack: ", "---------------over----------------");
            }

        });
    }
}
