package xyz.pixelatedw.MineMineNoMi3.quests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An simple annotation thats indicates to sync this field with the client. Stores this field in the nbt tag.
 * @author GardelJr
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SyncField {

}
