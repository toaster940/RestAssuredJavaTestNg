package pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    private long jobId;
    private String jobTitle;
    private String jobDescription;
    private List<String> experience;
    private List<Project> project;

}
