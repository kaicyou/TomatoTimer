import sys
import time

# import Tkinter module based on python version
if sys.version < '3':
    from Tkinter import *
else:
    from tkinter import *

# set time and cycles
work_time = 5
# rest_time = 5
working_cycles = 3


def show_message():
    # show reminder message window
    root = Tk()
    # root.minsize(500, 200)
    # root.maxsize(1000, 400)
    root.withdraw()
    # get width and height of the screen and make sure the window pops out at the middle
    screen_width = root.winfo_screenwidth()
    screen_height = root.winfo_screenheight() - 100  # Task bar involved
    root.wm_resizable(False, False)
    # add module
    root.title("Warning!")
    frame = Frame(root, relief=RIDGE, borderwidth=3)
    frame.pack(fill=BOTH, expand=1)
    # font setting
    label = Label(frame,
                  text="You have been working for 20 minutes! Please have a break!",
                  font="Times -20 bold")
    label.pack(fill=BOTH, expand=1)
    # buttons
    button = Button(frame, text="OK", font="Times -25 bold", fg="red", command=root.destroy)
    button.pack(side=BOTTOM)

    root.update_idletasks()
    root.deiconify()
    root.withdraw()
    root.geometry('%sx%s+%s+%s' % (root.winfo_width() + 10, root.winfo_height() + 10,
                                   (screen_width - root.winfo_width()) / 2, (screen_height - root.winfo_height()) / 2))
    root.deiconify()
    root.mainloop()

while working_cycles > 0:
    time.sleep(work_time)
    working_cycles -= 1
    show_message()