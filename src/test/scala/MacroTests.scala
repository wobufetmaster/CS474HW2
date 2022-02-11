import MySetTheoryDSL.*
import setExp.*
import org.scalatest.funsuite.AnyFunSuite

class MacroTests extends AnyFunSuite {
  test("Basic Macro Test") {
    CreateMacro("Add 3",Insert(Value(3))).eval()
    Assign(Variable("mySet"),Macro("Add 3")).eval()
    assert(Check("mySet",Value(3)))
    
  }
  test("Left to Right Execution Test") {
    CreateMacro("myMacro",Assign(Variable("myVariable"),Value(3))).eval() //Assigns myVariable to be 3. Remember that assign returns nothing!
    Assign(Variable("mySet"),Macro("myMacro"),Variable("myVariable")).eval() //Because Assign executes left to right, the myVariable is instantiated and then placed into mySet.
    assert(Check("mySet",Value(3)))

    Delete(Variable("myVariable")).eval()
    //Switching the order of the statements means myVariable doesn't exist when we try to add it to the set.
    assertThrows[NoSuchElementException](Assign(Variable("mySet"),Variable("myVariable"),Macro("myMacro")).eval())

  }

  test("Macro Test with Scopes") {

    CreateMacro("myMacro",Delete(Variable("mySet"))).eval()
    Scope("scopename",Scope("othername",Assign(Variable("mySet"),Value("This is the inner scope")))).eval()
    Scope("scopename",Assign(Variable("mySet"),Value("This is the outer scope"))).eval()
    Assign(Variable("mySet"),Value("This is the global scope")).eval()

    Scope("scopename",Scope("othername",Macro("myMacro"))).eval()
    assertThrows[NoSuchElementException](Check("mySet",Value(1),Some("othername")))

    assert(Check("mySet",Value("This is the outer scope"),Some("scopename"))) //The other elements shouldn't be deleted
    assert(Check("mySet",Value("This is the global scope")))

    Scope("scopename",Macro("myMacro")).eval()
    assertThrows[NoSuchElementException](Check("mySet",Value(1),Some("scopename")))
    Macro("myMacro").eval()
    assertThrows[NoSuchElementException](Check("mySet",Value(1)))


  }
}
