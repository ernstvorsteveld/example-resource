package nl.vorstdev.example.resource.resource;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by ernstvorsteveld on 29/02/16.
 */
@ConfigurationProperties(locations = "classpath:/appprops.yml", prefix = "endpoints")
public class ApplicationProperties {
    private String id;
    private String sensitive;
    private String enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
