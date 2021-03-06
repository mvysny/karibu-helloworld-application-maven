package org.test

import com.github.mvysny.karibudsl.v8.*
import javax.servlet.annotation.WebServlet

import com.vaadin.annotations.Theme
import com.vaadin.annotations.VaadinServletConfiguration
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinServlet
import com.vaadin.ui.UI
import org.slf4j.bridge.SLF4JBridgeHandler

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 *
 * The UI is initialized using [init]. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
class MyUI : UI() {

    override fun init(vaadinRequest: VaadinRequest) {
        verticalLayout {
            val name = textField("Type your name here:")
            button("Click Me") {
                onLeftClick {
                    val greeting = "Thanks ${name.value}, it works!"
                    this@verticalLayout.label(greeting)
                }
            }
        }
    }
}

@WebServlet(urlPatterns = ["/*"], name = "MyUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = MyUI::class, productionMode = false)
class MyUIServlet : VaadinServlet() {
    companion object {
        init {
            // Vaadin logs into java.util.logging. Redirect that, so that all logging goes through slf4j.
            SLF4JBridgeHandler.removeHandlersForRootLogger()
            SLF4JBridgeHandler.install()
        }
    }
}
