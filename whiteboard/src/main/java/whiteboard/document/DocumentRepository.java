package whiteboard.document;

import java.util.List;

import org.springframework.data.repository.Repository;

import whiteboard.lecture.*;

public interface DocumentRepository extends Repository<Document, Long> {
	
	List<Document> findAll();
	
	Document findById(int id);
	
	void save(Document document);
	 
	List<Document> findAllByLectureId(int lectureId);
	
	void deleteById(int id);
	
	
}
