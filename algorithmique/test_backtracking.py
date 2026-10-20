import backtracking

c = backtracking.Clause()


c.ajouter_litteral(1, True)
# print(c)
valeurs = {
    "1": True,
}
# print(valeurs)
print(c.evaluer(valeurs))
print(c.litteraux())


f = backtracking.Formule()
f.ajouter_clause(c)
print(f.litteraux())
c.ajouter_litteral(1, False)
print(c.est_tautologie())