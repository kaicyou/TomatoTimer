import sys

# import Tkinter module based on python version
if sys.version < '3':
    from Tkinter import *
else:
    from tkinter import *

fields = 'Work Time', 'Rest Time', 'Work Cycles'


def fetch(entries):
    for entry in entries:
        filed = entry[0]
        num = entry[1].get()
        print('%s: "%s"' % (filed, num))


def make_form(root, fields):
    entries = []
    for field in fields:
        row = Frame(root)
        lab = Label(row, width=15, text=field, anchor='w')
        ent = Entry(row)
        row.pack(side=TOP, fill=X, padx=5, pady=5)
        lab.pack(side=LEFT)
        ent.pack(side=RIGHT, expand=YES, fill=X)
        entries.append((field, ent))
    return entries


if __name__ == '__main__':
    root = Tk()
    ents = make_form(root, fields)
    root.bind('<Return>', (lambda event, e=ents: fetch(e)))
    b1 = Button(root, text="Show", command=(lambda e=ents: fetch(e)))
    b1.pack(side=LEFT, padx=5, pady=5)
    b2 = Button(root, text='Quit', command=root.quit)
    b2.pack(side=LEFT, padx=5, pady=5)
    root.mainloop()