package nl.hu.inno.hulp.users.data;

import nl.hu.inno.hulp.users.domain.Teacher;
import org.springframework.data.aerospike.repository.AerospikeRepository;

public interface TeacherRepository extends AerospikeRepository<Teacher, String> {
}