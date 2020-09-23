package com.linkedin.dagli.struct;

import java.util.Map;


/**
 * Struct is an interface for classes that have been autogenerated from @Struct definitions.  It's useful when one
 * wants to, e.g. accept Structs as arguments.
 */
public interface Struct {
  /**
   * Creates a map containing all values of this struct, with keys corresponding to the corresponding property/field
   * names.
   *
   * These field names always have a lower-case first character.  E.g. if a "FavoriteColor" field has defined in the
   * struct it will nonetheless have the key "favoriteColor" in the map.
   *
   * @return a map containing a value for every field in the struct
   */
  Map<String, Object> toMap();
}
