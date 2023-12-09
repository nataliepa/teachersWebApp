package gr.aueb.elearn.teachersWebApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeacherDTO {
    private String Id;
    private String oldFname;
    private String newFname;
    private String oldSname;
    private String newSname;
}
