package io.questdb.mp;

/**
 * Delegates next() to nextBully(). This way the next() always returns a valid sequence number.
 */
public final class BullyignSequence implements Sequence {

    private final Sequence delegate;

    public BullyignSequence(Sequence delegate) {
        this.delegate = delegate;
    }

    @Override
    public long availableIndex(long lo) {
        return delegate.availableIndex(lo);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public long current() {
        return delegate.current();
    }

    @Override
    public void done(long cursor) {
        delegate.done(cursor);
    }

    @Override
    public Barrier getBarrier() {
        return delegate.getBarrier();
    }

    @Override
    public WaitStrategy getWaitStrategy() {
        return delegate.getWaitStrategy();
    }

    @Override
    public long next() {
        return delegate.nextBully();
    }

    @Override
    public long nextBully() {
        return delegate.nextBully();
    }

    @Override
    public Barrier root() {
        return delegate.root();
    }

    @Override
    public void setBarrier(Barrier barrier) {
        delegate.setBarrier(barrier);
    }

    @Override
    public void setCurrent(long value) {
        delegate.setCurrent(value);
    }

    @Override
    public Barrier then(Barrier barrier) {
        return delegate.then(barrier);
    }

    @Override
    public long waitForNext() {
        return delegate.waitForNext();
    }
}
