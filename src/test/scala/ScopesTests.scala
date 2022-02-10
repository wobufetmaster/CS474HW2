import MySetTheoryDSL.*
import setExp.*
import org.scalatest.funsuite.AnyFunSuite

class ScopesTests extends AnyFunSuite {
  test("Basic Scopes Test") {

    Assign(Variable("someSetName"),Insert(Value(1),Value(3),Value(5))).eval()
    Scope("scopename", Assign(Variable("someSetName"), Insert(Value(2),Value(4),Value(6)))).eval()

    assert(Check("someSetName",Insert(Value(1),Value(3),Value(5)))) //Outer scope should be unnafected
    assert(Check("someSetName",Insert(Value(2),Value(4),Value(6)),Some("scopename"))) //Inner scope should have correct values

    assert(!Check("someSetName",Insert(Value(2),Value(4),Value(6)))) //Inner scope values shouldn't be in outer scope
    assert(!Check("someSetName",Insert(Value(1),Value(3),Value(5)),Some("scopename"))) //Outer scope values shouldn't be in inner scope

  }
  test("Delete Scopes Test") {
    Assign(Variable("someSetName"),Insert(Value(1),Value(3),Value(5))).eval()
    Scope("scopename", Scope("othername",Assign(Variable("someSetName"),Insert(Value("Scala"),Value("Is"),Value("Cool"))))).eval()

    assert(Check("someSetName",Insert(Value(1),Value(3),Value(5))))
    Delete(Variable("someSetName")).eval() //Delete from global scope
    assertThrows[NoSuchElementException](Check("someSetName", Value(1)))

    assert(Check("someSetName", Insert(Value("Scala"),Value("Is"),Value("Cool")), Some("othername"))) //Inner value intact after deletion

    Scope("othername",Delete(Variable("someSetName"))).eval()

    assertThrows[NoSuchElementException](Check("someSetName", Value(1)),Some("othername"))

  }
  test("Advanced Scopes Test") { //Do something with Variable
    assert(true)

  }

}
