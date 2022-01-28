# Homework 1
### Description: create a language for users of the set theory to create and evaluate binary operations on sets using variables and scopes where elements of the sets can be objects of any type.
### Grade: 10%

## Preliminaries
As part of this homework assignment you will gain experience with creating and managing your Git repository, implementing your first *domain-specific language (DSL)* using Scala for writing and evaluating set operation expressions for users of the [set theory](https://en.wikipedia.org/wiki/Set_theory). Using your DSL users can describe and evaluate [binary operations on sets](https://images-na.ssl-images-amazon.com/images/G/31/img15/books/tiles/9352036042_chemistry.pdf) using variables and scopes where elements of the sets can be objects of any type. You will create [Scalatest](https://www.scalatest.org/) tests to test your implementation and you will create [SBT build and run scripts](https://www.scala-sbt.org/) for your DSL project. Feel free to come up with some cute name for your language. Doing this homework is essential for successful completion of the rest of this course, since all other homeworks will share the same features of this homework: branching, merging, committing, pushing your code into your Git repo, creating test cases and build scripts, reusing the DSL that you will design and implement and employing various tools like a debugger for diagnosing problems with your applications.

First things first, you must create your account at [Github](https://github.com), a Git repo management system. Then invite me, your course instructor as your collaborator – my github ID is 0x1DOCD00D and your TA whose github ID is laxmena. Since it is a large class, please avoid direct emails from other accounts like funnybunny2000@gmail.com and use the corresponding channels on Teams instead. You will always receive a response within 12 hours at most.

Next, if you haven't done so, you will install [IntelliJ](https://www.jetbrains.com/student/) with your academic license, the JDK, the Scala runtime and the IntelliJ Scala plugin and the [Simple Build Toolkit (SBT)](https://www.scala-sbt.org/1.x/docs/index.html) and make sure that you can create, compile, and run Java and Scala programs. Please make sure that you can run [various Java tools from your chosen JDK between versions 8 and 17](https://docs.oracle.com/en/java/javase/index.html).

I recommend using [the fifth edition of the book on Programming in Scala by Martin Odersky and Lex Spoon et al](https://www.artima.com/shop/programming_in_scala_5ed). There are many other books and resources available on the Internet to learn Scala. Those who know more about functional programming can use the book on Functional Programming in Scala published on Sep 14, 2014 by Paul Chiusano and Runar Bjarnason and it is available using your academic subscription on [SafariBooksOnline](https://learning.oreilly.com/home/).

## Overview
In class I created and shared with you a simple arithmetic expression evaluation language embedded in Scala for multiplication and addition that uses a statically defined environment to specify values for variables. In this homework, you will create a similar language for binary set theory operation and you will add expressions for storing results of some computations in variables and using them in different scopes. In addition, you will add macros that will expand macro definitions in the expressions where the macro names are used.

You will implement the following operations.
- Insert into and delete an object from a set.
- Union of the sets A and B, denoted A ∪ B, is the set of all objects that are a member of A, or B, or both.
- Intersection of the sets A and B, denoted A ∩ B, is the set of all objects that are members of both A and B.
- Set difference of U and A, denoted U \ A, is the set of all members of U that are not members of A.
- Symmetric difference of sets A and B, denoted A ⊖ B, is the set of all objects that are a member of exactly one of A and B (elements which are in one of the sets, but not in both). For instance, for the sets {1, 2, 3} and {2, 3, 4}, the symmetric difference set is {1, 4}. It is the set difference of the union and the intersection, (A ∪ B) \ (A ∩ B) or (A \ B) ∪ (B \ A). 
- Cartesian product of A and B, denoted A × B, is the set whose members are all possible ordered pairs (a, b), where a is a member of A and b is a member of B.

Consider the following example of using your language.
```scala
//creating a set and populating it with objects. The operation Assign locates a set object
//given its name or creates a new one if it does not exist. The second parameter is the
//operation Insert that adds objects to the set. The first parameter of the operation Insert
//is an object that is referenced by the variable var, the second is an integer and the third is a string.
Assign(Variable("someSetName"), Insert(Variable("var"), Value(1)), Value("somestring"))
//check if an object is in the set
Check("someSetName", Value(1)) //it should return the boolean value true
//in this example we define a macro and use it in the set operation to delete an object
//referenced by the variable "var"
Macro("someName", Delete(Variable("var")))
Assign(Variable("someSetName"), Macro("someName"))
//this example shows how users can create scope definition and use
Scope("scopename", Scope("othername", Assign(Variable("someSetName"), Insert(Variable("var"), Value(1)), Value("somestring"))))
Assign(Scope("scopename", Scope("othername", Variable("someSetName"))), Insert(Value("x")))
```

This homework script is written using a retroscripting technique, in which the homework outlines are generally and loosely drawn, and the individual students improvise to create the implementation that fits their refined objectives. In doing so, students are expected to stay within the basic requirements of the homework and they are free to experiments. Asking questions is important, so please ask away on the corresponding Teams channels!

## Functionality
Thinking about a definition of a set as a collection of well defined objects which are distinct from each other you can define a set as a function S: Object => Boolean. That is, given an object the function, S that represents a set returns **true** if the object is in the set and **false** otherwise. Hence, your set implemented may use some collection classes like List or Map internally to store objects. 

In your language, scopes can be created dynamically as part of the expressions in addition to being predefined in the environment. Macros will use lazy evaluation to substitute the expression for a given macro name in the expressions where the macro name is used.

Your homework can be divided roughly into five steps. First, you learn how to configure and run your Scala project using the SBT with the code that I wrote in class. Second, you design your own language for set theory operations evaluation and you can create your homework by building on the class example. You will add the logic for binding set objects to variables, which will later be used in the extensions of this language in your next homeworks. Next, you will create an implementation of scopes, named and anonymous with scoping rules for obscuring and shadowing that you define to resolve the values of variables that have the same names in expressions. Fourth, you will create macros to substitute macro definitions for the used macro names in expressions. Finally, you will create Scalatest tests to verify the correctness of your implementation. You will write a report to explain your implementation and the semantics of your language. Add the corresponding entries to your build.sbt to enable Scalatest using the latest available version.
```scala
"org.scalatest" %% "scalatest" % scalacticVersion % Test,
"org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test,
```

An example of the Scalatest is shown below.
```scala
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class YourSetTheoryLanguageTest extends AnyFlatSpec with Matchers {
  behavior of "my first language for set theory operations"

  it should "create a set and insert objects into it" in {
    Assign(Variable("someSetName"), Insert(Variable("var"), Value(1)), Value("somestring")).eval()
    Check("someSetName", Value(1)).eval() shouldBe true
  }
```

## Baseline
To be considered for grading, your project should include the constructs Assign, Scope, Macro, and all required operations and your project should be buildable using the SBT, and your documentation must specify how you create and evaluate expressions in your language.

## Teams collaboration
You can post questions and replies, statements, comments, discussion, etc. on Teams. For this homework, feel free to share your ideas, mistakes, code fragments, commands from scripts, and some of your technical solutions with the rest of the class, and you can ask and advise others using Teams on language design issues, resolving error messages and dependencies and configuration issues. When posting question and answers on Teams, please select the appropriate folder, i.e., hw1 to ensure that all discussion threads can be easily located. Active participants and problem solvers will receive bonuses from the big brother :-) who is watching your exchanges on Teams (i.e., your class instructor). However, *you must not post the source code of your program or specific details on how your implemented your design ideas!*

## Git logistics
**This is an individual homework.** Separate private repositories will be created for each of your homeworks and for the course project. Inviting other students to join your repo for an individual homework will result in losing your grade. For grading, only the latest push timed before the deadline will be considered. **If you push after the deadline, your grade for the homework will be zero**. For more information about using the Git please use this [link as the starting point](https://confluence.atlassian.com/bitbucket/bitbucket-cloud-documentation-home-221448814.html). For those of you who struggle with the Git, I recommend a book by Ryan Hodson on Ry's Git Tutorial. The other book called Pro Git is written by Scott Chacon and Ben Straub and published by Apress and it is [freely available](https://git-scm.com/book/en/v2/). There are multiple videos on youtube that go into details of the Git organization and use.

I repeat, make sure that you will give the course instructor and your TA the read/write access to *your repository* so that we can leave the file feedback.txt with the explanation of the grade assigned to your homework.

## Discussions and submission
As it is mentioned above, you can post questions and replies, statements, comments, discussion, etc. on Teams. Remember that you cannot share your code and your solutions privately, but you can ask and advise others using Teams and StackOverflow or some other developer networks where resources and sample programs can be found on the Internet, how to resolve dependencies and configuration issues. Yet, your implementation should be your own and you cannot share it. Alternatively, you cannot copy and paste someone else's implementation and put your name on it. Your submissions will be checked for plagiarism. **Copying code from your classmates or from some sites on the Internet will result in severe academic penalties up to the termination of your enrollment in the University**. When posting question and answers on Teams, please select the appropriate folder, i.e., hw1 to ensure that all discussion threads can be easily located.


## Submission deadline and logistics
Friday, February 11 at 10PM CST by emailing to your TA and CCing to me the link to your Github repository. Your submission will include the code, your documentation with instructions and detailed explanations on how to assemble and deploy your program along with the tests and a document that explains the semantics of your language, and what the limitations of your implementation are. Again, do not forget, please make sure that you will give your instructor/TA the write access to your repository. Your name should be shown in your README.md file and other documents. Your code should compile and run from the command line using the commands **sbt clean compile test** and **sbt clean compile run** or the corresponding commands for Gradle. Also, you project should be IntelliJ friendly, i.e., your graders should be able to import your code into IntelliJ and run from there. Use .gitignore to exlude files that should not be pushed into the repo.

## Evaluation criteria
- the maximum grade for this homework is 10%. Points are subtracted from this maximum grade: for example, saying that 2% is lost if some requirement is not completed means that the resulting grade will be 10%-2% => 8%; if the core homework functionality does not work, no bonus points will be given;
- only some basic expression language is implemented without scopes and assignments and macros and nothing else is done: up to 10% lost;
- for each use of **var** instead of **val** 0.2% will be substracted from the maximum grade;
- for each non-spelling-related problem reported by the IntelliJ code analysis and inspection tool 0.2% will be substracted from the maximum grade;
- having less than five unit and/or integration tests that show how your implemented features work: up to 5% lost;
- missing comments and explanations from the program: up to 5% lost;
- no instructions in your README.md on how to install and run your program: up to 5% lost;
- the program crashes without completing the core functionality or it is incorrect: up to 10% lost;
- the documentation exists but it is insufficient to understand how you assembled and deployed all language components: up to 8% lost;
- the minimum grade for this homework cannot be less than zero.

That's it, folks!
