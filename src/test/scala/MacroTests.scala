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
    

  }
}
