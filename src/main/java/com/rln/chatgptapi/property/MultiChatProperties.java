package com.rln.chatgptapi.property;

public class MultiChatProperties {

    private String url = "https://api.openai.com/v1/chat/completions";

    private String model = "gpt-3.5-turbo";


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

}
