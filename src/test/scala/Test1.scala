import MySetTheoryDSL.setExp
import org.scalatest.funsuite.AnyFunSuite

class Test1 extends AnyFunSuite {
  test("Default test") {
    import setExp.*
    //val firstExpression = Sub(Add(Add(Value(2), Value(3)),Var("Adan")), Var("x")).eval
    println("Running test 1!")
    assert(true)
  }

}
