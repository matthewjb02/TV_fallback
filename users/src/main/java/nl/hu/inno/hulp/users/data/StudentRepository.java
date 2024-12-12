package nl.hu.inno.hulp.users.data;

import nl.hu.inno.hulp.users.domain.Student;
import org.springframework.data.aerospike.repository.AerospikeRepository;

public interface StudentRepository extends AerospikeRepository<Student, String> {
}