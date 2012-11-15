package com.janxspirit.scalatra

import org.scalatra._
import scalate.ScalateSupport

class BaseServlet extends ScalatraServlet with ScalateSupport {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
      </body>
    </html>
  }

}
