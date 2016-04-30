package gov.kiron.android.la.data;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;

/**
 * Question content.
 */
public interface Content extends Serializable {

    /**
     * Question that should be displayed (usually a text, may use HTML)
     */
    String getContent();

    /**
     * Determines whether a picture is available for this question.
     */
    boolean hasPicture();

    /**
     * Question picture
     * @return Image (if present)
     */
    Bitmap getPicture();
}
