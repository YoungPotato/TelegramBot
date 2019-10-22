package main_package.io;
import main_package.questions.Question;
import org.apache.commons.lang.StringEscapeUtils;
import java.util.Scanner;

public class IOImpl implements IO {
    @Override
    public void printStart() {
        System.out.println("Привет, я консольный бот. Я умею задавать вопросы.");
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

    @Override
    public void write(Question question) {
        print(question.question);
        for (int i = 0; i < 4; i++) {
            print(String.format("%s. %s", i + 1, question.answers.get(i)));
        }
    }

    private void print(String text) {
        System.out.println(StringEscapeUtils.unescapeJava(text
                .replace("\\u2063", ""))
                .replace("\"", ""));
    }

    @Override
    public void printHelp() {
        System.out.println("Вы попали в игру \"Как стать миллионером\". Чат-бот будет выдавать вам вопросы, чтобы ответить на них, вводите цифру или текст с ответом в консоль. Удачи! \n");
    }

    @Override
    public void correctAnswer() {
        System.out.println("Верно! \n");
    }

    @Override
    public void incorrectAnswer() {
        System.out.println("Неверно! \n");
    }
}
