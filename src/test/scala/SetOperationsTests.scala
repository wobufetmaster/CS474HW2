import MySetTheoryDSL.*
import setExp.*
import org.scalatest.funsuite.AnyFunSuite

class SetOperationsTests extends AnyFunSuite {
  test("Product Test") {

    println("Running test 1!")
    Assign(Variable("ProductSet"),Product(Insert(Value(1),Value(3)),Insert(Value(2),Value(4)))).eval()

    Check("ProductSet", NestedInsert(
      Insert(Value(1),Value(2)),
      Insert(Value(1),Value(4)),
      Insert(Value(3),Value(2)),
      Insert(Value(3),Value(4))))
  }
  
  test("Symmetric Difference Test") {
    println("Running test 2!")
    Assign(Variable("someSetName"), SymmetricDifference(Insert(Value(1),Value(2),Value(3)),Insert(Value(2),Value(3),Value(4)))).eval()
    //println(Variable("someSetName").eval())
    assert(Check("someSetName", Insert(Value(1),Value(4))))

  }
  test("Difference Test") {
    println("Running test 3!")
    Assign(Variable("someSetName"), Difference(Insert(Value(1),Value(2),Value(3)),Insert(Value(2),Value(3),Value(4)))).eval()
    assert(Check("someSetName", Value(1)))

  }
  test("Intersection Test") {
    println("Running test 4!")
    Assign(Variable("someSetName"), Intersection(Insert(Value(1),Value(2),Value(3)),Insert(Value(2),Value(3),Value(4)))).eval()
    assert(Check("someSetName", Insert(Value(2),Value(3))))


  }
  test("Union Test") {
    println("Running test 5!")
    Assign(Variable("someSetName"), Union(Insert(Value(1),Value(2),Value(3)),Insert(Value(2),Value(3),Value(4)))).eval()
    println(Variable("someSetName").eval())
    assert(Check("someSetName", Insert(Value(2),Value(3),Value(4),Value(1))))
  }

}
