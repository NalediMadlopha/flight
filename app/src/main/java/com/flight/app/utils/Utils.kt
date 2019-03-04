package com.flight.app.utils

import java.text.SimpleDateFormat

class Utils private constructor() {

    companion object {
        private const val PARSER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        private const val FORMATTER_DATE_FORMAT = "HH:mm"

        @JvmStatic
        fun formatDateTime(date: String): String {
            val parser = SimpleDateFormat(PARSER_DATE_FORMAT)
            val formatter = SimpleDateFormat(FORMATTER_DATE_FORMAT)
            return formatter.format(parser.parse(date))

        }
    }
}