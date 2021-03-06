/*
 * The MIT License
 *
 * Copyright 2013 SBPrime.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primesoft.asyncworldedit.blockPlacer;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import org.bukkit.World;
import org.primesoft.asyncworldedit.worldedit.AsyncEditSession;

/**
 *
 * @author Prime
 */
public class BlockPlacerGetBlockEntry extends BlockPlacerEntry {

    private final Vector m_location;
    private final Object m_mutex = new Object();
    private BaseBlock m_result = null;

    @Override
    public boolean isDemanding() {
        return false;
    }
    
    public Vector getLocation() {
        return m_location;
    }

    public Object getMutex() {
        return m_mutex;
    }

    public BaseBlock getResult() {
        return m_result;
    }

    public BlockPlacerGetBlockEntry(AsyncEditSession editSession,
            int jobId, Vector location) {
        super(editSession, jobId);
        m_location = location;
    }

    @Override
    public void Process(BlockPlacer bp) {
        final World world = m_editSession.getCBWorld();       
        synchronized (m_mutex) {
            m_result = m_editSession.doRawGetBlock(m_location);
            m_mutex.notifyAll();
        }
    }
}
