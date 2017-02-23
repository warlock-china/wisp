package cn.com.warlock.wisp.core.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Mysql column name
 */
@Data
public class MysqlColumn {

    @SerializedName("n")
    private String name;

    @SerializedName("t")
    private String mysqlType;

    @SerializedName("v")
    private String value;

    @SerializedName("origin_val")
    private String originValue;

    @SerializedName("null")
    private boolean isNull;

    @SerializedName("updated")
    private boolean isUpdated;
}
