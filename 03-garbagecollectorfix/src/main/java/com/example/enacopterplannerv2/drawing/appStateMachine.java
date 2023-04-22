package com.example.enacopterplannerv2.drawing;

public class appStateMachine extends StateMachine{
    public appStateMachine() {
    }

    public State move = new State(){
        Transition onDrawingMode = new Transition(){
          @Override
          public void action(){
              //rendre impossible le fait de pouvoir bouger la map
          }

          @Override
            public State goTo(){
              return wait;
          }
        };
    };

    public State wait = new State(){
        Transition offDrawingMode = new Transition(){
          @Override
          public void action(){
              //rendre à nouveau possible le fait de pouvoir bouger la map
          }

          @Override
            public State goTo(){
              return move;
          }
        };

        Transition onLongTouch = new Transition(){
            @Override
            public State goTo(){
                return survol;
            }
        };

        Transition onTouch = new Transition(){
            @Override
            public State goTo(){
                return draw;
            }
        };
    };

    public State survol = new State(){
        Transition onScroll = new Transition(){
            @Override
            public void action(){
                //action pour afficher un cercle fantomatique quand on fait un appui long
            }
        };

        Transition onRelease = new Transition(){
            @Override
            public void action(){
                //affichage du cercle fantomatique précédemment affiché pour de vrai (donc vraie couleur)
            }

            @Override
            public State goTo(){
                return wait;
            }
        };
    };

    public State draw = new State() {
        Transition onScroll = new Transition(){
            @Override
            public void action(){
                //on dessine sur la map
            }
        };

        Transition onRelease = new Transition(){
            @Override
            public State goTo(){
                return wait;
            }
        };
    };
}
