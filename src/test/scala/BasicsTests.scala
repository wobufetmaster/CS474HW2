import MySetTheoryDSL.*
import setExp.*
import org.scalatest.funsuite.AnyFunSuite

class BasicsTests extends AnyFunSuite {
  test("Assign Test") {
    println("Running test 1!")
    Assign(Variable("someSetName"), Value(1), Value("somestring"), Value(0x0f)).eval()

    assert(Check("someSetName", Value(1)))
    assert(Check("someSetName", Value("somestring")))
    assert(Check("someSetName", Value(0x0f)))

    Assign(Variable("someSetName"), Value(2), Value("otherstring"), Value(Set())).eval() //Reassignment should override old map

    assert(Check("someSetName", Value(2))) //Make sure new values are in set
    assert(Check("someSetName", Value("otherstring")))
    assert(Check("someSetName", Value(Set())))

    assert(!Check("someSetName", Value(1))) //Old values should not be in set
    assert(!Check("someSetName", Value("somestring")))
    assert(!Check("someSetName", Value(0x0f)))


  }
  test("Insert Test") {
    println("Running test 2!")
    Assign(Variable("someSetName"), Insert(Value(1), Value("somestring"))).eval()
    assert(Check("someSetName", Insert(Value(1),Value("somestring"))))

    Assign(Variable("someOtherSetName"), Insert(Value(3.14), Insert(Insert(Insert(Value("nestedval")))))).eval()
    assert(Check("someOtherSetName", Insert(Value(3.14), Insert(Value("nestedval")))))
  }

  test("Nested Insert Test") {
    println("Running test 3!")
    Assign(Variable("someSetName"), NestedInsert(Value(1), Value("somestring"))).eval()
    assert(Check("someSetName", Insert(NestedInsert(Value(1)),NestedInsert(Value("somestring")))))

  }

  test("Delete Test") {

    println("Running test 4!")
    Assign(Variable("someSetName"), Insert(Value(9999), Value("somestring"))).eval()
    assert(Check("someSetName", Value(9999)))

    Delete(Variable("someSetName")).eval()
    assertThrows[NoSuchElementException](Check("someSetName", Value(9999)))

  }

  test("Variable Test") {

    println("Running test 5!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("3"), Value(5)).eval()

    Assign(Variable("myOtherSet"), Insert(Variable("someSetName"),Value(777777))).eval()


    Assign(Variable("myOtherSet"), Insert(Variable("myOtherSet"),Value(777777))).eval() //Note that inserting the set into itself should do nothing.
    assert(Check("someSetName", Insert(Value(1), Value("3"), Value(5))))
    println(Variable("myOtherSet").eval())
    assert(Check("myOtherSet", Insert(Value(1), Value("3"), Value(5), Value(777777))))

  }


}
