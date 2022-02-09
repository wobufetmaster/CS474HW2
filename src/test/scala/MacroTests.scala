import MySetTheoryDSL.*
import org.scalatest.funsuite.AnyFunSuite

class MacroTests extends AnyFunSuite {
  test("Basics Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 1!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))
    assert(Check("someSetName", Value("somestring")))

  }
  test("Delete Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 2!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))

  }
  test("Set Operations Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 3!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))

  }
  test("Scopes Test") {
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
  test("Macro Test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 5!")
    Assign(Variable("someSetName"), Insert(Value(1)), Value("somestring")).eval()
    assert(Check("someSetName", Value(1)))

  }

}
