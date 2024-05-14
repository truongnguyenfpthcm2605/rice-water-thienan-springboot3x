package org.website.thienan.ricewaterthienan.exceptions;

public class PermissionDeniedException extends  RuntimeException{
   public PermissionDeniedException(String message) {
      super(message);
   }
}
