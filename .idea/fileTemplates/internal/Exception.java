/**
 *  Exception: ${NAME}
 *  Custom exception for handling specific error scenarios.
 *  
 *  Created by: ${USER}
 *  On: ${YEAR}/${MONTH_NAME_SHORT}
 *  
 *  GitHub: https://github.com/RGerva
 *  
 *  Copyright (c) ${YEAR} @RGerva. All Rights Reserved.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */

#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};

#end
#parse("File Header.java")
public class ${NAME} extends RuntimeException {
  public ${NAME}(String message) {
    super(message);
  }
}
