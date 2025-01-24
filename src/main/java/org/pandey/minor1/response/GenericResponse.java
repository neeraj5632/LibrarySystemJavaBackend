package org.pandey.minor1.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenericResponse <T>{

    private T data;

    private String Error;

    private String message; //success or failure

    private String  code;
}
