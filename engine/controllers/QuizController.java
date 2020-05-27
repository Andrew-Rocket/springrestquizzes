package engine.controllers;

import engine.entities.Completion;
import engine.entities.Quiz;
import engine.entities.User;
//import engine.repos.CompRepo;
import engine.repos.CompRepo;
import engine.repos.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/api")
public class QuizController {

    @Autowired
    QuizRepo quizRepo;
    @Autowired
    CompRepo compRepo;

    @Autowired
    public QuizController(QuizRepo quizRepo) {
        this.quizRepo = quizRepo;
    }

    @GetMapping(path = "/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        if (quizRepo.findById(id).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Not found"
            );
        }
        return quizRepo.findById(id).get();
    }


    @GetMapping(path = "/quizzes")
    public Page<Quiz> getAllQuizzes(Pageable pageable) {
        Page<Quiz> view = quizRepo.findAllQuizzesWithPagination(pageable);
        return view;
    }


    @GetMapping(path = "/quizzes/completed")
    public Page<Completion> getAllCompletions(Pageable pageable,
                                              @AuthenticationPrincipal User user) {

        Page<Completion> view = compRepo.findAllCompletionsWithPagination(user.getId(), pageable);
//        return view.map((x)-> {
//            if(x.getUserId()!=user.getId()) {
//                return null;
//            }
//            return x;
//        });
        return view;
    }

    @PostMapping(path = "/quizzes/{id}/solve")
    public LinkedHashMap<String, Object> solveQuiz(@RequestBody HashMap<String, List<Integer>> answer,
                                                   @PathVariable int id,
                                                   @AuthenticationPrincipal User user) {
        Quiz quiz = getQuiz(id);

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if (quiz.checkAnswer(answer.get("answer"))) {
            map.put("success", true);
            map.put("feedback", "Congratulations, you're right!");
            compRepo.save(new Completion(quiz.getId(), user.getId()));
        } else {
            map.put("success", false);
            map.put("feedback", "Wrong answer! Please, try again");
        }
        return map;
    }

    @PostMapping(path = "/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz,
                        @AuthenticationPrincipal User user) {
        quiz.setUser(user);
        quizRepo.save(quiz);
        return quiz;
    }

    @DeleteMapping(path = "/quizzes/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(name = "id") int id,
                                            @AuthenticationPrincipal User user) {
        Quiz quiz = quizRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (quiz.getUser().getId() == user.getId()) {
            quizRepo.delete(quiz);
            return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }


}
