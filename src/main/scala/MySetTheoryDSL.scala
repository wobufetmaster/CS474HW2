import MySetTheoryDSL.setExp.*

import scala.collection.mutable

object MySetTheoryDSL:
  type BasicType = Any

  private val macro_map: collection.mutable.Map[String, setExp] = collection.mutable.Map("set1"->setExp.Value(5))
  private val scope_map: collection.mutable.Map[(String,Option[String]), Set[Any]] = collection.mutable.Map(("set1",None)->Set())
  private val current_scope: mutable.Stack[String] = new mutable.Stack[String]()

  def get_scope(name: String): Option[String] = {
    for (i <- current_scope) {
      scope_map get (name,Some(i)) match {
        case Some(value) => return Some(i)
        case None =>
      }
    }
    None
  }

  enum setExp:
    case Value(input: BasicType)
    case Variable(name: String)
    case Macro(name: String)
    case Create_Macro(name: String, op2: setExp)
    case Scope(name: String, op2:setExp)
    case Assign(name: Variable, op2: setExp*)
    case Insert(op: setExp*)
    case Delete(name: Variable)
    case Union(op1: setExp, op2: setExp)
    case Intersection(op1: setExp, op2: setExp)
    case Difference(op1: setExp, op2: setExp)
    case SymmetricDifference(op1: setExp, op2: setExp)
    case Product(op1: setExp, op2: setExp)


    def eval(): Set[Any] = { //Walks through the AST and returns a set. Set[Any]
      this match {
        case Value(v) => Set(v)
        case Variable(name) => scope_map(name,current_scope.headOption)
        case Macro(a) => macro_map(a).eval()
        case Create_Macro(a,b) =>
          macro_map.update(a,b)
          Set()
        case Scope(a,b) =>
          current_scope.push(a) //Push current scope onto stack
          val temp = b.eval() //Evaluate rhs
          current_scope.pop() //Current scope is over - go back to previous scope
          temp //Return the evaluated value
        case Assign(Variable(name), set*) =>
          scope_map.update((name,current_scope.headOption),set.foldLeft(Set())((v1,v2) => v1 | v2.eval()))
          //println(scope_map(name,current_scope.headOption))
          Set()
        case Insert(to_insert*) => to_insert.foldLeft(Set())((v1,v2) => v1 | v2.eval())
        case Delete(Variable(name)) =>
          scope_map.remove(name,current_scope.headOption)
          Set()
        case Union(op1, op2) => op1.eval() + op2.eval()
        case Intersection(op1, op2) => op1.eval() & op2.eval()
        case Difference(op1, op2) => op1.eval() -- op2.eval()
        case SymmetricDifference(op1, op2) => op1.eval() &~ op2.eval()
        case Product(op1, op2) => op1.eval() + op2.eval()


        //case default => Set()
      }
    }
    ////All of the other cases return a set, except for check, which returns a boolean, so this is a special case. As a result it does not need to be evaluated.
  def Check(set_name: String, set_val: setExp.Value, set_scope: Option[String] = None): Boolean = {  //the Scope can be optionally supplied, or global scope will be used if omitted.
    set_val.eval().subsetOf(scope_map(set_name,set_scope))
  }

  @main def runSetExp(): Unit =
    import setExp.*


    //Scope("scopename", Scope("othername", Assign(Variable("someSetName"), Insert(Variable("var"), Value(1))))).eval()


