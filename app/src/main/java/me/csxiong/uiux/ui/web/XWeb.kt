package me.csxiong.uiux.ui.web

import me.csxiong.library.integration.http.XHttp
import me.csxiong.library.utils.ThreadExecutor
import me.csxiong.uiux.utils.print
import java.net.InetSocketAddress
import java.net.ServerSocket

class XWeb {

    init {
        Thread {
            var server = ServerSocket()
            server.bind(InetSocketAddress("http://localhost",5555))
            while (true) {
                var socket = server.accept()
                var byteArray = ByteArray(2048)
                var sb = StringBuilder()
                while (socket.getInputStream().read(byteArray) != -1) {
                    sb.append(byteArray)
                }
                sb.toString().print("cccsssxxx")
            }
        }.start()

        ThreadExecutor.runOnUiThread(Runnable {
            Thread {
                var host = XHttp.getInstance().httpClient.socketFactory().createSocket("http://127.0.0.1", 5566)
                host?.connect(InetSocketAddress("http://localhost", 5555))
                host.getOutputStream().write("fadsfadsfaadf".toByteArray())
            }.start()
        },2000)

        ThreadExecutor.runOnUiThread(Runnable {
            Thread {
                var host = XHttp.getInstance().httpClient.socketFactory().createSocket("http://localhost", 5567)
                host?.connect(InetSocketAddress("http://localhost", 5555))
                host.getOutputStream().write("fadsfadsfaadf".toByteArray())
            }.start()
        },2000)

        ThreadExecutor.runOnUiThread(Runnable {
            Thread {
                var host = XHttp.getInstance().httpClient.socketFactory().createSocket("http://localhost", 5568)
                host?.connect(InetSocketAddress("http://localhost", 5555))
                host.getOutputStream().write("fadsfadsfaadf".toByteArray())
            }.start()
        },2000)

        ThreadExecutor.runOnUiThread(Runnable {
            Thread {
                var host = XHttp.getInstance().httpClient.socketFactory().createSocket("http://localhost", 5569)
                host?.connect(InetSocketAddress("http://localhost", 5555))
                host.getOutputStream().write("fadsfadsfaadf".toByteArray())
            }.start()
        },2000)

    }
}
