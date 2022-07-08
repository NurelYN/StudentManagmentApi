package com.example.studentdomain.dto.studentEnrollment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentEnrollmentSaveRequest {

    private String courseName;

    private Long studentId;
}
