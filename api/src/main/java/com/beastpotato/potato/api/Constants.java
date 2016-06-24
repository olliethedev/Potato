package com.beastpotato.potato.api;

/**
 * Created by Oleksiy on 2/6/2016.
 */
public class Constants {
    public enum Http {
        GET(0),
        POST(1),
        PUT(2),
        DELETE(3),
        HEAD(4),
        OPTIONS(5),
        TRACE(6),
        PATCH(7);
        private int numValue;

        Http(int numValue) {
            this.numValue = numValue;
        }

        public int getNumValue() {
            return this.numValue;
        }
    }

    public enum ModelType {
        GSON_COMPAT, GSON_AND_ORM_LITE_COMPAT
    }
}
