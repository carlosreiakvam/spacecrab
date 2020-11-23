function out = poassoCumu(lambda,from,to)
out = 0;
for i = from:to
    out = out+ poassoEq(lambda,i);
end