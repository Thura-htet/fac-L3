import random


class Clause:
    def __init__(self):
        self.clause = dict()

    def ajouter_litteral(self,lit,neg=False):
        # la mÃ©thode ajouter litteral doit ajouter le littÃ©ral lit
        # sous forme nÃ©gative si neg est Ã  True
        s = str(lit)
        if not self.clause.get(s):
            self.clause[s] = [False, False]
        
        if neg:
            self.clause[s][1] = True
        else:
            self.clause[s][0] = True

    def forme_lit(self, lit):
        return self.clause[lit][0] or False
        
    def evaluer(self,valeurs):
        # evaluer prend en paramÃªtre un dictionnaire de valeurs et
        # doit renvoyer True si la clause est vraie avec ces valeurs,
        # False si elle ne l'est pas et None s'il manque des valeurs 
        # pour pouvoir Ã©valuer la clause.
        # valeur => (lit: forme)
        for lit in self.clause: # think about iterating over clause
            # immediately return True if tautology or evaluates to True
            '''
            if self.clause[lit][0]:
                res = valeurs[lit]
            
            if self.clause[lit][1]:
                res = not valeurs[lit]
            '''
            if lit not in valeurs:
                return None

            print(f"{self.forme_lit(lit)} and {valeurs[lit]}")
            if self.forme_lit(lit) == valeurs[lit]: # False and False is False
                return True
        return False

    def litteraux(self):
        # cette mÃ©thode doit renvoyer une liste ou un itÃ©rateur sur
        # les littÃ©raux contenus dans la clause : si un littÃ©ral x, un
        # littÃ©ral Â¬x et un littÃ©ral y sont dans la clause, x et y devront 
        # Ãªtre Ã©numÃ©rÃ©s, mais une seule fois chacun.
        return self.clause.keys()

    def __str__(self):
        # cette mÃ©thode doit renvoyer la clause sous une forme lisible par
        # un Ãªtre humain 
        res = ""
        for lit in self.clause.keys():
            # not if else because it can be both
            if self.clause[lit][0]:
                res += lit + " ∨ "
            if self.clause[lit][1]:
                res += "¬" + lit + " ∨ "
        return res[:-3]
            

    def est_tautologie(self):
        # Question Bonus
        for lit in self.clause.keys():
            if self.clause[lit][0] and self.clause[lit][1]:
                return True
        return False

class Formule:
    def __init__(self):
        self.clauses = list()


    def ajouter_clause(self,clause):
        self.clauses.append(clause)
        # i think when you add a clause you should redefine litteraux

    def litteraux(self):
        # cette mÃ©thode doit renvoyer une liste ou un itÃ©rateur sur
        # les littÃ©raux contenus dans la formule.
        res = set()
        for clause in self.clauses:
            res.update(clause.litteraux())
        return res

    def evaluer(self,valeurs):
        # evaluer prend en paramÃªtre un dictionnaire de valeurs et
        # doit renvoyer True si la clause est vraie avec ces valeurs
        # False si ce n'est pas possible et None si avec les valeurs
        # fournies on ne peut pas encore dÃ©cider.
        for clause in self.clauses:
            if not clause.evaluer(valeurs):
                return False
        return True

    def __str__(self):
        # cette mÃ©thode doit renvoyer la formule sous une forme lisible par
        # un Ãªtre humain " ∧ "
        res = ""
        start = True
        for clause in self.clauses:
            if not start:
                res += " ∧ " 
            res += f" ({str(clause)}) "
            start = False

        return res

    def satisfiable(self):
        # Ã  l'aide de backtracking, cette mÃ©thode doit renvoyer false si la
        # formule n'est pas satisfiable et un dictionnaire associant littÃ©raux
        # et valeur rendant la formule vraie sinon.
        res = dict()
        litteraux = self.litteraux()
        
        # if at any point a clause returns False the formula is not satisfiable
        # under the current ==> this is not true
        def backtrack(c=0, l=0):
            if c > len(self.clauses): # isn't it only if it's greater than?
                return res
            clause = self.clauses[c]
            
            litteraux = list(clause.litteraux())
            litteral = litteraux[l]

            # you'd have to test the existing value if it exists
            # otherwise test the opposite
            # clause.evaluer(res) can return three values
            # True => keep it and move on to another CLAUSE 
            # None => keep it and move on to another LITTERAL
            # False => assign LITTERAL to False
            if litteral not in res:
                res[litteral] = True

            print(f"Clause: {self.clauses[c]}")
            print(f"Res: {res}")
            
            satisfiable = clause.evaluer(res)
            print(satisfiable)
            print()
            if satisfiable == None: # means there are still other litterals that are not initiated
                res[litteral] = True # or continue needs to be removed i think
                backtrack(c, l+1)
            elif not satisfiable:
                if res[litteral] != False: # it would be stuck here if it is not for this condition
                    res[litteral] = False
                    backtrack(c, l)
                else:
                    return False
            else: # the condition is satisfiable
                if backtrack(c+1, 0) is not False:
                    return True
             
            # need to check the bounds of c and l
        satisfiable = backtrack(0, 0)
        if satisfiable:
            return res
        return satisfiable


    @staticmethod
    def random(nb_vars,nb_clauses,clause_maxsize,seed=None):
        # Une mÃ©thode statique pour gÃ©nÃ©rer des formules alÃ©atoires, la seed vous permettra 
        # de tester avec quelques exemples bien choisis
        if seed is not None:
            random.seed(seed)
        f = Formule()
        for i in range(nb_clauses):
            k = random.randint(1,clause_maxsize)
            c = Clause()
            for j in range(k):
                c.ajouter_litteral("x"+str(random.randint(0,nb_vars)),bool(random.getrandbits(1)))
            f.ajouter_clause(c)
        return f

    def simplifier(self):
        # Question Bonus
        pass


if __name__=="__main__":
    f = Formule.random(10,10,3)
    print(f)
    print(f.satisfiable())
    
    f = Formule.random(1,1,5,seed=10)
