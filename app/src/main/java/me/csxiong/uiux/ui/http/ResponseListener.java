package me.csxiong.uiux.ui.http;

import okhttp3.Response;

/**
 * Desc : 默认响应监听
 * Author : csxiong - 2019/6/24
 */
public interface ResponseListener<T> {

    /**
     * 转化响应体过程
     *
     * @param response 完整的OKHttp响应体
     * @return 是否接管解析过程 true -> 是 ,false -> 否
     * 如果接管解析过程,以下默认解析方式 全部失效
     * {@link #onNext(T)}
     * {@link #onError(Throwable)}
     * {@link #onComplete()}
     */
    default boolean onConvertResponse(Response response) {
        return false;
    }

    /**
     * 数据响应体
     *
     * @param t 数据体
     */
    void onNext(T t);
    /**
     * 异常响应
     *
     * @param throwable 异常
     */
    default void onError(Throwable throwable) {
    }

    /**
     * 请求结束
     * 仅在onNext结果回来伴随调用
     */
    default void onComplete() {

    }

}
