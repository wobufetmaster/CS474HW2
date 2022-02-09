import MySetTheoryDSL.*
import org.scalatest.funsuite.AnyFunSuite

class BasicsTest extends AnyFunSuite {
  test("Assign Test") {
    import setExp.*
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

    //assertThrows[NoSuchElementException](Check("someSetName", Value(Set())))

  }
  test("Insert Test") {
    import setExp.*
    println("Running test 2!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))

  }
  test("Delete Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 2!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))

  }
  test("Value Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 3!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))

  }
  test("Variable Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 4!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("3"), Value(5)).eval()
    Scope("scopename", Scope("othername", Assign(Variable("someSetName"), Insert(Value(2), Value(4)), Value("someotherstring")))).eval()

    assert(Check("someSetName", Value(1)))
    assert(Check("someSetName", Value("3")))
    assert(Check("someSetName", Value(5)))

    assert(Check("someSetName", Value(2),Some("othername")))
    assert(Check("someSetName", Value(4),Some("othername")))
    assert(Check("someSetName", Value("someotherstring"),Some("othername")))



  }


}
