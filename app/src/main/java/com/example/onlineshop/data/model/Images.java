package com.example.onlineshop.data.model;

import com.google.gson.annotations.SerializedName;

public class Images {

        @SerializedName("id")
        private int mId;
        @SerializedName("src")
        private String mSrc;

        public int getId() {
            return mId;
        }

        public void setId(int id) {
            mId = id;
        }

        public String getSrc() {
            return mSrc;
        }

        public void setSrc(String src) {
            mSrc = src;
        }

        public Images(int id, String src) {
            mId = id;
            mSrc = src;
        }

    }
