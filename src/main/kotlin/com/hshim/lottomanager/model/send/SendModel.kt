package com.hshim.lottomanager.model.send

const val MESSAGE_HEADER_TEXT = """
    <html>
        <head>
          <style>
            body {
              font-family: Arial, sans-serif;
              margin: 0;
              padding: 20px;
              line-height: 1.6;
            }
            h1 {
              font-size: 1.5em;
              margin-bottom: 16px;
            }
            h2 {
              font-size: 1.2em;
              margin-top: 32px;
              margin-bottom: 8px;
            }
            table {
              width: 100%;
              border-collapse: collapse;
              margin-top: 8px;
            }
            th, td {
              border: 1px solid #ccc;
              padding: 8px;
              text-align: center;
            }
            th {
              background-color: #f2f2f2;
            }
            .footer-info {
              margin-top: 32px;
              padding: 20px;
              background-color: #f9f9f9;
              border-top: 1px solid #ddd;
            }
            .footer-info p {
              margin: 4px 0;
            }
            .footer-title {
              font-weight: bold;
              margin-bottom: 8px;
            }
            .footer-link {
              color: #0066cc;
              text-decoration: none;
            }
          </style>
        </head>
        <body>
"""

const val MESSAGE_FOOTER_TEXT = """
        <div class="footer-info">
        <div class="footer-title">문의 및 정보</div>
        <p>개발자 이메일 문의: <a href="mailto:ggrteas@gmail.com" class="footer-link">ggrteas@gmail.com</a></p>
        <p>인스타그램: <a href="https://instagram.com/hshim__" target="_blank" class="footer-link">@hshim__</a></p>
      </div>
        </body>
    </html>
"""

abstract class SendModel(
    val title: String,
    val message: String,
) {
    val html = MESSAGE_HEADER_TEXT + message + MESSAGE_FOOTER_TEXT
}