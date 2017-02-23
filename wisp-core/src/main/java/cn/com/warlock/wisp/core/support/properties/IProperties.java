package cn.com.warlock.wisp.core.support.properties;

public interface IProperties {

    String getProperty(String item, String defaultValue);

    String getProperty(String item);
}
