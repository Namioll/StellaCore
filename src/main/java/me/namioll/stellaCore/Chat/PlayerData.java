package me.namioll.stellaCore.Chat;

public class PlayerData {
    private String prefix;
    private String title;
    private String nickcolor;
    private String messagecolor;
    private String link;
    private boolean streamer;
    private boolean colors;
    private boolean hex;

    public PlayerData(String prefix, String title, String nickcolor, String messagecolor, String link, boolean streamer, boolean colors, boolean hex) {
        this.prefix = prefix;
        this.title = title;
        this.nickcolor = nickcolor;
        this.messagecolor = messagecolor;
        this.link = link;
        this.streamer = streamer;
        this.colors = colors;
        this.hex = hex;
    }

    public void setStreamer(boolean streamer){
        this.streamer = streamer;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getTitle() {
        return title;
    }

    public String getNickcolor() {
        return nickcolor;
    }

    public String getMessagecolor() {
        return messagecolor;
    }

    public String getLink() {
        return link;
    }

    public boolean isStreamer() {
        return streamer;
    }

    public boolean isColors() {
        return colors;
    }

    public boolean isHex() {
        return hex;
    }
}
