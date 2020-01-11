
package com.example.animall.Data.Remote.Models.LikeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("0")
    @Expose
    private Boolean _0;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public Boolean get0() {
        return _0;
    }

    public void set0(Boolean _0) {
        this._0 = _0;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
