package de.btobastian.javacord.entities.message.embed.impl;

import com.fasterxml.jackson.databind.JsonNode;
import de.btobastian.javacord.entities.message.embed.Embed;
import de.btobastian.javacord.entities.message.embed.EmbedAuthor;
import de.btobastian.javacord.entities.message.embed.EmbedField;
import de.btobastian.javacord.entities.message.embed.EmbedFooter;
import de.btobastian.javacord.entities.message.embed.EmbedImage;
import de.btobastian.javacord.entities.message.embed.EmbedProvider;
import de.btobastian.javacord.entities.message.embed.EmbedThumbnail;
import de.btobastian.javacord.entities.message.embed.EmbedVideo;
import de.btobastian.javacord.utils.logging.LoggerUtil;
import org.slf4j.Logger;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The implementation of {@link Embed}.
 */
public class ImplEmbed implements Embed {

    /**
     * The logger of this class.
     */
    private static final Logger logger = LoggerUtil.getLogger(ImplEmbed.class);

    private final String title;
    private final String type;
    private final String description;
    private final String url;
    private final Instant timestamp ;
    private final Color color;
    private final EmbedFooter footer;
    private final EmbedImage image;
    private final EmbedThumbnail thumbnail;
    private final EmbedVideo video;
    private final EmbedProvider provider;
    private final EmbedAuthor author;
    private final List<EmbedField> fields = new ArrayList<>();

    /**
     * Creates a new embed.
     *
     * @param data The json data of the embed.
     */
    public ImplEmbed(JsonNode data) {
        title = data.has("title") ? data.get("title").asText() : null;
        type = data.has("type") ? data.get("type").asText() : null;
        description = data.has("description") ? data.get("description").asText() : null;
        url = data.has("url") ? data.get("url").asText() : null;
        timestamp = data.has("timestamp") ? OffsetDateTime.parse(data.get("timestamp").asText()).toInstant() : null;
        color = data.has("color") ? new Color(data.get("color").asInt()) : null;
        footer = data.has("footer") ? new ImplEmbedFooter(data.get("footer")) : null;
        image = data.has("image") ? new ImplEmbedImage(data.get("image")) : null;
        thumbnail = data.has("thumbnail") ? new ImplEmbedThumbnail(data.get("thumbnail")) : null;
        video = data.has("video") ? new ImplEmbedVideo(data.get("video")) : null;
        provider = data.has("provider") ? new ImplEmbedProvider(data.get("provider")) : null;
        author = data.has("author") ? new ImplEmbedAuthor(data.get("author")) : null;
        if (data.has("fields")) {
            for (JsonNode jsonField : data.get("fields")) {
                this.fields.add(new ImplEmbedField(jsonField));
            }
        }
    }

    @Override
    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public Optional<URL> getUrl() {
        if (url == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(new URL(url));
        } catch (MalformedURLException e) {
            logger.warn("Seems like the url of the embed is malformed! Please contact the developer!", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Instant> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    @Override
    public Optional<Color> getColor() {
        return Optional.ofNullable(color);
    }

    @Override
    public Optional<EmbedFooter> getFooter() {
        return Optional.ofNullable(footer);
    }

    @Override
    public Optional<EmbedImage> getImage() {
        return Optional.ofNullable(image);
    }

    @Override
    public Optional<EmbedThumbnail> getThumbnail() {
        return Optional.ofNullable(thumbnail);
    }

    @Override
    public Optional<EmbedVideo> getVideo() {
        return Optional.ofNullable(video);
    }

    @Override
    public Optional<EmbedProvider> getProvider() {
        return Optional.ofNullable(provider);
    }

    @Override
    public Optional<EmbedAuthor> getAuthor() {
        return Optional.ofNullable(author);
    }

    @Override
    public List<EmbedField> getFields() {
        return fields;
    }
}