package com.celine.imagesteganography.errors

import java.lang.RuntimeException

class FieldMissingException(fieldName: String): RuntimeException("$fieldName not provided")