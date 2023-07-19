package jp.gihyo.pro.java.tasklist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/rest")
public class HomeRestController {
    @RequestMapping(value = "/hello")
    @ResponseBody
    String hello() {
        return """
                <html>
                   <head><title>Hello</title></head>
                   <body>
                     <h1>Hello</h1>
                     It works!<br>
                     現在時刻は%sです。
                   </body>
                </html>
                """.formatted(LocalDateTime.now());
    }

    record TaskItem(String id, String task, String deadline, boolean done) {}
    private List<TaskItem> taskItems = new ArrayList<>();

    @GetMapping("/add")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id,task,deadline,false);
        taskItems.add(item);
        return "タスクを追加しました。";
    }

    @GetMapping("/list")
    String listItems() {
        return taskItems.stream().map(t -> t.toString())
                .collect(Collectors.joining(", "));
    }
}
