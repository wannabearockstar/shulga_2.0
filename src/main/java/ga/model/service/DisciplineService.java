package ga.model.service;

import ga.model.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wannabe on 15.04.16.
 */
@Service
public class DisciplineService {
	@Autowired
	private DisciplineRepository disciplineRepository;
}
